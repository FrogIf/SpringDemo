package frog.learn.spring.converter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * 注入到spring容器之后, springmvc会自动查找, 识别, 应用到该使用的转换中
 * 这个是给spring mvc使用的, 不是给jackson使用的, 要分清楚!!!
 */
@Component
public class MoneyFormatter implements Formatter<Money> {

    @Override
    public Money parse(String text, Locale locale) throws ParseException {
        if(NumberUtils.isParsable(text)){
            return Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(text));
        }else if(StringUtils.isNotEmpty(text)){
            String[] t = StringUtils.split(text," ");
            if(t != null && t.length == 2 && NumberUtils.isParsable(t[1])){
                return Money.of(CurrencyUnit.of(t[0]), NumberUtils.createBigDecimal(t[1]));
            }else{
                throw new ParseException(text, 0);
            }
        }
        throw new ParseException(text, 0);
    }

    @Override
    public String print(Money money, Locale locale) {
        if(money == null){
            return null;
        }
        return money.getCurrencyUnit().getCode() + " " + money.getAmount();
    }
}
