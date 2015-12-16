package ru.crystals.egais;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import ru.crystals.egais.answer.Answer;
import ru.crystals.egais.answer.AnswerURL;
import ru.crystals.egais.answer.ObjectFactory;

public class AnswerController {
    private static ObjectFactory answerFactory = new ObjectFactory();

    protected static String makeAnswer(String sign, String... urls) throws IOException, JAXBException {
        Answer answer = answerFactory.createAnswer();
        answer.setVer(1);
        answer.getUrl().addAll(Arrays.stream(urls).map(s -> new AnswerURL(s)).collect(Collectors.toList()));
        answer.setSign(sign);
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            Marchallers.getMarshaller(Answer.class).marshal(answer, result);
            return result.toString();
        }
    }

}
