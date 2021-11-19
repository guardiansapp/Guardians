package com.guardians.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.guardians.model.NotificacaoGrupo;
import com.guardians.model.NotificacaoGuardiao;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

@Service
public class SendNotificationService {

	public void send(NotificacaoGuardiao notificacao) {

		String url = "https://api.expo.dev/v2/push/send";

		try {

			ObjectMapper mapper = new ObjectMapper();

			JsonNode childNode1 = mapper.createObjectNode();
			((ObjectNode) childNode1).put("to", notificacao.getGuardiao().getNotificationToken());
			((ObjectNode) childNode1).put("title", notificacao.getUsuario().getNome() + " precisa de ajuda.");
			((ObjectNode) childNode1).put("body", notificacao.getNotificacao().getMensagem());
			
			String json = childNode1.toPrettyString();
			
			RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

			Request request = new Request.Builder()
					.method("POST", body)
					.url(url)
					.header("Content-Type", "application/json")
					.header("Accept-encoding", "gzip, deflate")
					.header("Accept", "application/json")
					.build();

			new OkHttpClient().newCall(request).execute();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

	public void send(NotificacaoGrupo notificacao) {

		String url = "https://api.expo.dev/v2/push/send";

		try {

			ObjectMapper mapper = new ObjectMapper();

			JsonNode childNode1 = mapper.createObjectNode();
			((ObjectNode) childNode1).put("to", notificacao.getId().getUsuario().getNotificationToken());
			((ObjectNode) childNode1).put("title", notificacao.getId().getGrupo().getNome() + " - " + notificacao.getId().getNotificacao().getUsuario());
			((ObjectNode) childNode1).put("body", notificacao.getId().getNotificacao().getMensagem());
			
			String json = childNode1.toPrettyString();
			
			RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

			Request request = new Request.Builder()
					.method("POST", body)
					.url(url)
					.header("Content-Type", "application/json")
					.header("Accept-encoding", "gzip, deflate")
					.header("Accept", "application/json")
					.build();

			new OkHttpClient().newCall(request).execute();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
