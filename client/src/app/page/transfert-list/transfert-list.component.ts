import { Component, OnInit } from '@angular/core';
import { TransferService } from '../../services/transfer.service';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { KeycloakService } from 'keycloak-angular';

interface Column {
  field: string;
  header: string;
}

@Component({
  selector: 'app-transfert-list',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule],
  templateUrl: './transfert-list.component.html',
  styleUrl: './transfert-list.component.scss'
})
export class TransfertListComponent implements OnInit {

  transferts !: any;
  cols: Column[] = [];
  colsTx: Column[] = [];
  expandedRows = {};
  companyLogged !: any;
  constructor(private transfertService: TransferService, private keyclokService: KeycloakService) { }
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

}
