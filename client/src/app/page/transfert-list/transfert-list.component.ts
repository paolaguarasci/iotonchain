import { Component, OnInit } from '@angular/core';
import { TransferService } from '../../services/transfer.service';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { KeycloakService } from 'keycloak-angular';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ToastModule } from 'primeng/toast';
import { TransportService } from '../../services/transport.service';
interface Column {
  field: string;
  header: string;
}

@Component({
  selector: 'app-transfert-list',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, ConfirmPopupModule, ToastModule],
  templateUrl: './transfert-list.component.html',
  styleUrl: './transfert-list.component.scss',
  providers: [ConfirmationService, MessageService]
})
export class TransfertListComponent implements OnInit {

  transferts !: any;
  cols: Column[] = [];
  colsTx: Column[] = [];
  expandedRows = {};
  companyLogged !: any;
  constructor(private transfertService: TransferService, private transportService: TransportService,  private keyclokService: KeycloakService, private confirmationService: ConfirmationService, private messageService: MessageService) { }
  async ngOnInit() {

    if (this.keyclokService.isLoggedIn()) {
      let userProfile: any = await this.keyclokService.loadUserProfile();
      this.companyLogged = userProfile['attributes']['company'][0];
      console.log("Company logged ", this.companyLogged);
    }

    this.update()
  }

  update() {
    this.transfertService.getAll().subscribe({
      next: (res: any) => {
        this.transferts = res;

        this.generateCols();

      },
      error: (err: any) => {

      }
    })
  }


  generateCols() {
    if (this.transferts && this.transferts.length > 0) {
      let keys = Object.keys(this.transferts[0])
      keys.map((key: any) => {
        if (!(this.transferts[0][key] instanceof Object)) {
          this.cols.push({
            field: key,
            header: key
          })
        } else {
          if (this.transferts[0][key][0]) {
            let keysInner = Object.keys(this.transferts[0][key][0])
            keysInner.map((keyi: any) => {
              this.colsTx.push({
                field: keyi,
                header: keyi
              })
            })
          }
        }
      })
    }
  }

  expandAll() {
    this.expandedRows = this.transferts.reduce((acc: any, p: any) => (acc[p.id] = true) && acc, {});
  }

  collapseAll() {
    this.expandedRows = {};
  }

  accept(tx: any) {
    this.transfertService.acceptOne(tx.id).subscribe({
      next: (res: any) => {
        this.update();
      }, error: (err: any) => { }
    })
  }

  reject(tx: any) {
    this.transfertService.rejectOne(tx.id).subscribe({
      next: (res: any) => {
        this.update();
      }, error: (err: any) => { }
    })
  }


  abort(tx: any) {
    this.transfertService.abortOne(tx.id).subscribe({
      next: (res: any) => {
        this.update();
      }, error: (err: any) => { }
    })
  }

  sendTruck(transfert: any) {
    this.transportService.createOne({
      batchId: transfert.oldBatchID,
      location: "",
      companyFrom: transfert.companySenderUsername,
      companyTo: transfert.companyRecipientUsername,
    }).subscribe({
      next: (res: any) => {
        console.log("Res ", res)
        this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'You have accepted', life: 3000 });
      },
      error: (err: any) => {
        this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected', life: 3000 });
      },
    })

  }

  sendTruckConfirm(event: any, tx: any) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Are you sure you want to proceed?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.sendTruck(tx);
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected', life: 3000 });
      }
    });
  }
}


control.add("batch(b1, b, 10).");
control.add("batch(b2, b, 10).");
control.add("batch(b3, b, 10).");
control.add("batch(a1, a, 10).");
control.add("batch(a1, a, 10).");
control.add("batch(c1, c, 10).");


batch(b1, b, 10). batch(b2, b, 10). batch(b3, b, 10). batch(a1, a, 10). batch(a1, a, 10). batch(c1, c, 10).
