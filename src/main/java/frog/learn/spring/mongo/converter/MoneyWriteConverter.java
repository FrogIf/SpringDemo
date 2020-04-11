package frog.learn.spring.mongo.converter;

import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

public class MoneyWriteConverter implements Converter<Money, Long> {
    @Override
    public Long convert(Money money) {
        return money.getAmountMinorLong();
    }

//    @Override
//    public Money convert(Document document) {
//        Document money = (Document) document.get("money");
//        double amount = Double.parseDouble(money.getString("amount"));
//        String currency = ((Document) money.get("currency")).getString("code");
//        return Money.of(CurrencyUnit.of(currency), amount);
//    }
}
