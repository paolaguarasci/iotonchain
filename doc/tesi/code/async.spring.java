// Visto che si sta usando Java > 21
// l'attivazione dei thread virtuali è sicuramente più semplice

// CONTROLLARE da quale versione di Java è vera questa cosa, 
// sicuramente con la 22 funziona ma non sono sicura se inizia
// a funzionare dalla 20 o dalla 21! 

// Basta inserire la seguente righe nelle applicazion.properties

// spring.threads.virtual.enabled=true
// spring.thread-executor=virtual

// Configurazione
@EnableAsync
@Configuration
@ConditionalOnProperty(
  value = "spring.thread-executor",
  havingValue = "virtual"
)
public class ThreadConfig {
  @Bean
  public AsyncTaskExecutor applicationTaskExecutor() {
    return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
  }
  
  @Bean
  public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
    return protocolHandler -> {
      protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    };
  }
}
