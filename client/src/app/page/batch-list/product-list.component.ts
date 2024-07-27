import { Component, OnInit, ViewChild } from '@angular/core';
import { CompanyBatchService } from '../../services/company-batch.service';

import { MessageService, SelectItem } from 'primeng/api';
import { DataViewModule } from 'primeng/dataview';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { CommonModule } from '@angular/common';
import { DropdownModule } from 'primeng/dropdown';
import { InputSwitchModule } from 'primeng/inputswitch';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputTextModule } from 'primeng/inputtext';
import { DialogModule } from 'primeng/dialog';
import { CheckboxModule } from 'primeng/checkbox';
import { RadioButtonModule } from 'primeng/radiobutton';
import { FormsModule } from '@angular/forms';
import { CompanyService } from '../../services/company.service';
import { TransferService } from '../../services/transfer.service';
import { ToastModule } from 'primeng/toast';
import { KeycloakService } from 'keycloak-angular';
import { OverlayPanel, OverlayPanelModule } from 'primeng/overlaypanel';
import { TableModule } from 'primeng/table';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    DataViewModule,
    ButtonModule,
    TagModule,
    CommonModule,
    DropdownModule,
    FormsModule,
    InputGroupModule,
    InputGroupAddonModule,
    InputTextModule,
    InputSwitchModule,
    DialogModule,
    CheckboxModule,
    RadioButtonModule,
    ToastModule,
    OverlayPanelModule,
    TableModule,
  ],
  providers: [
    CompanyBatchService,
    CompanyService,
    MessageService,
    TransferService,
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss',
})
export class ComapnyBatchListComponent implements OnInit {
  products: any[] = [];
  companyClients: any[] = [];
  selectedQuantity = 0;
  selectedCompanyClient: any = null;
  selectedTransferType!: any;
  selectedBatch!: any;
  visibleTrasferDialog: boolean = false;
  infobatch: any[] = [];

  @ViewChild('txdetails', { static: false }) txdetails!: OverlayPanel;

  constructor(
    private productService: CompanyBatchService,
    private companyService: CompanyService,
    private transferService: TransferService,
    private messageService: MessageService,
    private keycloakService: KeycloakService,
    private router: Router
  ) { }
  async ngOnInit() {
    this.updateList();
    this.updateClients();

    let profile = await this.keycloakService.loadUserProfile();
    console.log(profile);
  }

  updateList() {
    this.productService.getAll().subscribe({
      next: (res: any) => {
        this.products = res;
      },
      error: (err: any) => { },
    });
  }
  updateClients() {
    this.companyService.getAllClients().subscribe({
      next: (res: any) => {
        this.companyClients = res;
      },
      error: (err: any) => { },
    });
  }


  goToLanding(id: any) {
  }
  goToTrack(id: any) {
    this.router.navigate(['/track', id])
  }

  showTrasferDialog(items: any) {
    this.visibleTrasferDialog = true;
    this.selectedBatch = items;
  }

  showInfoDialog(item: any, $event: any) {
    this.infobatch = []
    this.transferService.getAllByBatchId(item.batchId).subscribe({
      next: (res: any) => {
        res.map((tx: any) => {
          this.infobatch.push({
            date: tx.transferDateStart,
            from: tx.companySenderUsername,
            to: tx.companyRecipientUsername,
            batchId: tx.newBatchId,
          });
        });
        this.txdetails.show($event);
        console.log(res);
      },
      error: (err: any) => {
        console.log(err);
      },
    });
  }

  handleCleanup() {
    this.visibleTrasferDialog = false;
  }

  save() {
    this.visibleTrasferDialog = false;
    let dataToTransfer = {
      batchID: this.selectedBatch.batchId,
      quantity: this.selectedQuantity,
      unity: 'kg',
      companyRecipientID: this.selectedCompanyClient.id,
      type: this.selectedTransferType,
    };
    console.log(dataToTransfer);

    this.transferService.createOne(dataToTransfer).subscribe({
      next: (res: any) => {
        console.log(res);
        this.updateList();
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Trasfer completed successfully',
          life: 3000,
        });
      },
      error: (err: any) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error occurred',
          life: 3000,
        });
      },
    });
  }
}
