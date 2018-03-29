package com.sec.cryptohdslibrary.security;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

 
//SIGNATURES SO SAO USADAS NUM SENTIDO, no outro o servidor usa HMAC para n ter que criar uma signature a cada msg de resposta.TODO

public class DigSignature {

	public byte[] sign(byte[] data, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		
		// generating a signature
		Signature rsaForSign = Signature.getInstance("SHA256withRSA");
		rsaForSign.initSign(privateKey);
		rsaForSign.update(data);
		byte[] signature = rsaForSign.sign();
		return signature;
	}
	
	
	public boolean verifySignature(byte[] pubKeyClient,byte[] signature,byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
		
		KeyFactory keyFact = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(pubKeyClient);
		PublicKey pubKey = keyFact.generatePublic(publicKeySpec);
		
		
		Signature rsaForVerify = Signature.getInstance("SHA256withRSA");
		rsaForVerify.initVerify(pubKey );
		rsaForVerify.update(data);
		boolean verifies = rsaForVerify.verify(signature);
		return verifies;
	}

	
	public byte[] timestamped(byte[] data){
		
		int ts = (int)(System.currentTimeMillis()/1000); //TODO verificar que a data esta correcta
		byte[] timeStamp = new byte[]{
		        (byte) (ts >> 24),
		        (byte) (ts >> 16),
		        (byte) (ts >> 8),
		        (byte) ts};
		        
		byte[] timeStampedData = new byte[timeStamp.length + data.length];
		System.arraycopy(data, 0, data,0, data.length);
		System.arraycopy(timeStamp,0, timeStampedData,data.length , timeStamp.length);
		return timeStampedData;
	}
	
	public boolean verifyTimeStamp(byte[] msg1, byte[] msg2) {//TODO Possivelmente em vez de usar timeStamps usar um numero que idenfica a mensagem em que vamos, incrementado pelo servidor.Mesmo esquema, + curto/facil de manipular.
		byte[] timeStampOnly1 = Arrays.copyOfRange(msg1,msg1.length-24,msg1.length);
		byte[] timeStampOnly2 = Arrays.copyOfRange(msg2,msg1.length-24,msg2.length);
		
		return Arrays.equals(timeStampOnly1,timeStampOnly2);	
	}
	
	/*
		Exemplo de chamada Register:
			Client:
				->Criar objecto LedgerDTO
					-> Para que o passo seguinte funcione é preciso que os DTO's implementem Serializable.
					-> Acho que a melhor solução passa por passar os DTO's para a Library, visto que o Client e o Server vao partilha-los
				->Converter Objecto em byte[]
					->Criar este metodo algures na library, numa classe Util

						public byte[] encodeObjectToByte(Object object) {
						  ByteArrayOutputStream bos = new ByteArrayOutputStream();
						  ObjectOutputStream oos = new ObjectOutputStream(bos);
						  oos.writeObject(object);
						  oos.flush();
						  return bos.toByteArray();
						}

				->Assinar Objecto e retornar a String. Usar isto: String base64.encode(byte[])
				->Criar TimeStamp ou Chave Gerada
				->Gerar Envelope
					-> Criar uma classe partilhada na Library com os seguintes atributos
						originalMessage: Object -> Acho que este Object pode ser uma abstraçao de todos os nossos DTO's
						signedMessage: String
						TimeStamp: Date
				->{Envelope}Ks -> Cifrar o envelope com uma Chave Publica, e retornar String base64.encode(byte[])
				->O que será enviado no body da Mensagem será sempre uma String em base64 que contem tudo cifrado

			Server:
			O objectivo aqui é criar um layer interno de segurança. Para isso deve ser usado o Spring Interceptor, isto permite que a API que criamos funcione independemente da camada de segurança que estamos a tentar definir.

			LER:
				http://www.baeldung.com/spring-mvc-handlerinterceptor
				https://stackoverflow.com/questions/38360215/how-to-create-a-spring-interceptor-for-spring-restful-web-services

				->Tem um Spring Interceptor
					->Decifra o body com a privateKey do servidor, obtendo assim o Envelope original
					->Ve se timestamp/Chave Gerada faz sentido, caso contrário return false
					->Valida a signature da originalMessage com a SignedMessage batem certo
					->Entra na API normal
	* */
	
	
	
}
