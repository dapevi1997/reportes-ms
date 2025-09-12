package co.com.crediya.dynamodb.helper;

import co.com.crediya.dynamodb.DynamoDBTemplateAdapter;
import co.com.crediya.dynamodb.ReporteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class TemplateAdapterOperationsTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<ReporteEntity> customerTable;

    private ReporteEntity reporteEntity;

    @BeforeEach
    void setUp() {
/*        MockitoAnnotations.openMocks(this);

        when(dynamoDbEnhancedAsyncClient.table("table_name", TableSchema.fromBean(ReporteEntity.class)))
                .thenReturn(customerTable);

        reporteEntity = new ReporteEntity();
        reporteEntity.setId("id");
        reporteEntity.setAtr1("atr1");*/
    }

    @Test
    void modelEntityPropertiesMustNotBeNull() {
/*        ReporteEntity reporteEntityUnderTest = new ReporteEntity("id", "atr1");

        assertNotNull(reporteEntityUnderTest.getId());
        assertNotNull(reporteEntityUnderTest.getAtr1());*/
    }

    @Test
    void testSave() {
/*        when(customerTable.putItem(reporteEntity)).thenReturn(CompletableFuture.runAsync(()->{}));
        when(mapper.map(reporteEntity, ReporteEntity.class)).thenReturn(reporteEntity);

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.save(reporteEntity))
                .expectNextCount(1)
                .verifyComplete();*/
    }

    @Test
    void testGetById() {
/*        String id = "id";

        when(customerTable.getItem(
                Key.builder().partitionValue(AttributeValue.builder().s(id).build()).build()))
                .thenReturn(CompletableFuture.completedFuture(reporteEntity));
        when(mapper.map(reporteEntity, Object.class)).thenReturn("value");

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.getById("id"))
                .expectNext("value")
                .verifyComplete();*/
    }

    @Test
    void testDelete() {
/*        when(mapper.map(reporteEntity, ReporteEntity.class)).thenReturn(reporteEntity);
        when(mapper.map(reporteEntity, Object.class)).thenReturn("value");

        when(customerTable.deleteItem(reporteEntity))
                .thenReturn(CompletableFuture.completedFuture(reporteEntity));

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.delete(reporteEntity))
                .expectNext("value")
                .verifyComplete();*/
    }
}