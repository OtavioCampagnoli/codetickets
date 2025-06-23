package br.com.alura.codetickets;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDate;

@Configuration
public class ImportacaoConfiguration {

    private final PlatformTransactionManager transationsManager;

    public ImportacaoConfiguration(PlatformTransactionManager transationsManager) {
        this.transationsManager = transationsManager;
    }

    @Bean
    public Job job(Step primeiroPasso, JobRepository jobRepository) {
        return new JobBuilder("geracao-tickets", jobRepository)
                .start(primeiroPasso)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step passoInicial(JobRepository jobRepository, ItemReader<Importacao> reader,
                             ItemWriter<Importacao> writer) {
        return new StepBuilder("passo-inicial", jobRepository)
                .<Importacao, Importacao>chunk(200, transationsManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Importacao> reader() {
        return new FlatFileItemReaderBuilder<Importacao>()
                .name("leitura-csv")
                .resource(new FileSystemResource("files/dados.csv"))
                .comments("--")
                .delimited()
                .delimiter(";")
                .names("cpf", "cliente", "nascimento", "evento", "data", "tipoIngresso", "horaImportacao")
                .targetType(Importacao.class)
                .build();
    }

    @Bean
    public ItemWriter<Importacao> writer(DataSource dataSource) {
        String sql = "INSERT INTO importacao(id, cliente, cpf, data, evento, hora_importacao, nascimento, tipo_igresso) +" +
                "VALUES(:id, :cliente, :cpf, data:, :evento, " + LocalDate.now() + ", :nascimento, :tipoIgresso)";
        return new JdbcBatchItemWriterBuilder<Importacao>()
                .dataSource(dataSource)
                .sql(sql)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }
}
