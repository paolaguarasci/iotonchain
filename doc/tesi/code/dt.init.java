import com.azure.core.credential.TokenCredential;
import com.azure.digitaltwins.core.DigitalTwinsClient;
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;

@Service
public class DtServiceImpl implements DtService {  
  private final DigitalTwinsClient digitalTwinsClientSync;
  
  public DtServiceImpl() {
    this.digitalTwinsClientSync = new DigitalTwinsClientBuilder()
      .credential(new DefaultAzureCredentialBuilder().build())
      .endpoint(dturl)
      .buildClient();
  }
}