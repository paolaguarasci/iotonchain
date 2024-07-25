import { Component, OnInit } from '@angular/core';
import { DocumentService } from '../../services/document.service';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import { FileUploadModule, UploadEvent } from 'primeng/fileupload';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';
import { BadgeModule } from 'primeng/badge';
import { HttpClientModule } from '@angular/common/http';
import { ProgressBarModule } from 'primeng/progressbar';
import { ToastModule } from 'primeng/toast';
import { FormsModule } from '@angular/forms';
import { ethers } from 'ethers';
import { sha3_256 } from 'js-sha3';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-documents-list',
  standalone: true,
  imports: [
    FileUploadModule,
    ButtonModule,
    BadgeModule,
    ProgressBarModule,
    FormsModule,
    ToastModule,
    HttpClientModule,
    CommonModule,
  ],
  providers: [MessageService],
  templateUrl: './documents-list.component.html',
  styleUrl: './documents-list.component.scss',
})
export class DocumentsListComponent implements OnInit {
  files = [];
  myfile!: any;
  totalSize: number = 0;
  documents!: any;
  totalSizePercent: number = 0;
  selectedFile!: any;
uploading = false;
  hashCalculated!: any;
  txId!: any;

  constructor(
    private documentService: DocumentService,
    private config: PrimeNGConfig,
    private messageService: MessageService
  ) {}
  ngOnInit(): void {
    this.up();
  }

  up() {
    this.documentService.getAllDocuments().subscribe({
      next: (res: any) => {
        this.documents = res;
      },
      error: (err: any) => {},
    });
  }

  calculateHash() {
    const file = this.selectedFile;

    if (!file) {
      alert('Nessun file caricato!');
      return;
    }

    const reader = new FileReader();
    let that = this;
    reader.onload = function (e: any) {
      const buffer = e.target.result;
      that.hashCalculated = sha3_256(buffer);
      console.log(`${that.hashCalculated}`);
    };
    reader.readAsArrayBuffer(file);
  }
  upload() {
    if (this.selectedFile) {
      this.uploading = true;
    const formData = new FormData();
      formData.append('file', this.selectedFile);
      this.documentService.uploadDocument(formData).subscribe({
        next: (res: any) => {
          this.up();
          this.txId = res?.notarize?.txTransactionList[0]?.txId;
          this.uploading = false;
          this.messageService.add({
            severity: 'info',
            summary: 'Success',
            detail: 'File Uploaded',
          });
        },
        error: (err: any) => {
          this.uploading = false;
          this.messageService.add({
            severity: 'warning',
            summary: 'Error',
            detail: 'File Uploaded error',
          });
        },
      });
    }
  }
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  async check() {
    // URL del provider (qui si utilizza Infura, ma si possono usare altri provider come Alchemy o un nodo locale)
    const providerUrl =
      'https://polygon-amoy.infura.io/v3/fed6b76bf1004a159dd69d39a0824618';

    // Creazione di un provider
    const provider = new ethers.JsonRpcProvider(providerUrl);
    const contractAddress = environment.hashContract
    const abi: any = environment.hashABI
    // Hash della transazione che si desidera leggere
    const transactionHash =
      '0xfaaa24f7237e88ecb1fcb19ca9a8d2d0a73aaae801206c3adfad0585c4198ef3';

    try {
      const contract = new ethers.Contract(contractAddress, abi, provider);

      const transaction = await provider.getTransaction(transactionHash);
      if (transaction) {
        const data = transaction.data;
        const decodedInput = contract.interface.parseTransaction({ data });
        console.log(decodedInput);
        console.log('Dettagli della Transazione:');
        console.log(transaction);
      } else {
        console.log('Transazione non trovata');
      }
    } catch (error) {
      console.error('Errore nel recupero della transazione:', error);
    }

    // const provider = new ethers.JsonRpcApiProvider();
    // const signer = new ethers.Wallet(process.env.PRIVATE_KEY, provider);

    //   const tx = await signer.sendTransaction({
    //     to: '0x92d3267215Ec56542b985473E73C8417403B15ac',
    //     value: ethers.utils.parseUnits('0.001', 'ether'),
    //   });
    //   console.log(tx);

    // const Hash = await ethers.;
    // const proxyAddress = environment.hashContract;
    // const hash: Hash = Hash.attach(proxyAddress);
    // let check = await hash.isHashSigned(hashForSolidity);
  }

  onUpload(event: UploadEvent) {
    this.messageService.add({
      severity: 'info',
      summary: 'Success',
      detail: 'File Uploaded with Basic Mode',
    });
  }
}
