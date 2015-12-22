package ru.crystals.egais;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import ru.crystals.egais.answer.Answer;
import ru.crystals.egais.answer.AnswerURL;
import ru.crystals.egais.answer.ObjectFactory;
import ru.crystals.egais.exceptions.EgaisException;

public class AnswerController {
    private static ObjectFactory answerFactory = new ObjectFactory();

    protected static String makeAnswer(String sign, String... urls) throws IOException, JAXBException {
        return makeAnswer(sign, null, urls);
    }

    protected static String makeAnswer(EgaisException e) throws IOException, JAXBException {
        return makeAnswer(null, e);
    }

    protected static String makeAnswer(String sign, EgaisException e, String... urls) throws IOException, JAXBException {
        Answer answer = answerFactory.createAnswer();
        answer.setVer(1);
        if (e != null) {
            answer.setError(e.getMessage());
        } else {
            answer.getUrl().addAll(Arrays.stream(urls).map(s -> new AnswerURL(s)).collect(Collectors.toList()));
            answer.setSign(sign);
        }
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            Marchallers.getMarshaller(Answer.class).marshal(answer, result);
            return result.toString();
        }
    }

}
