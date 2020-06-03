package br.com.examples.springbatchbasics.writters;

import br.com.examples.springbatchbasics.configurations.ApplicationProperties;
import br.com.examples.springbatchbasics.models.ForeignCustomerOutput;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ForeignCustomerItemWriter extends FlatFileItemWriter<ForeignCustomerOutput> {

    private static final String DEFAULT_DELIMITER = ";";

    private static final String ID_HEADER = "Id";
    private static final String FIRST_NAME_HEADER = "first name";
    private static final String LAST_NAME_HEADER = "last name";
    private static final String GENDER_HEADER = "gender";
    private static final String BIRTHDAY_HEADER = "birthday";
    private static final String ADDRESS_HEADER = "address";
    private static final String COUNTRY_HEADER = "country";

    @Autowired
    ApplicationProperties properties;

    public ForeignCustomerItemWriter(ApplicationProperties properties) {
        super();

        final DelimitedLineAggregator<ForeignCustomerOutput> lineAggregator = new DelimitedLineAggregator<ForeignCustomerOutput>();
        final BeanWrapperFieldExtractor<ForeignCustomerOutput> fieldExtractor = new BeanWrapperFieldExtractor<ForeignCustomerOutput>();

        this.setResource(new ClassPathResource(properties.getAmericansFile()));
        this.setAppendAllowed(true);

        fieldExtractor.setNames(new String[]{ID_HEADER, FIRST_NAME_HEADER, LAST_NAME_HEADER, GENDER_HEADER, BIRTHDAY_HEADER, ADDRESS_HEADER, COUNTRY_HEADER });

        lineAggregator.setDelimiter(DEFAULT_DELIMITER);
        lineAggregator.setFieldExtractor(fieldExtractor);

        this.setLineAggregator(lineAggregator);
    }

}
