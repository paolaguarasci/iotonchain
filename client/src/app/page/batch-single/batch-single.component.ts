import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { CompanyBatchService } from '../../services/company-batch.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { QRCodeModule } from 'angularx-qrcode';
import { environment } from '../../../environments/environment';
import { ChipsModule } from 'primeng/chips';
import { TransferService } from '../../services/transfer.service';

@Component({
  selector: 'app-batch-single',
  standalone: true,
  imports: [CommonModule, ButtonModule, QRCodeModule, ChipsModule],
  templateUrl: './batch-single.component.html',
  styleUrl: './batch-single.component.scss'
})
export class BatchSingleComponent implements OnInit {

  baseUrlQrData !: any;
  batch !: any;
  batchTruck !: any;
  infobatch !: any;
  id!: any;
  isMine !: any;
  companyLogged !: any;
  qrData !: any;
  companyOwnerName !: any;

  constructor(    private transferService: TransferService, private keycloakService: KeycloakService, private batchService: CompanyBatchService, private router: Router, private route: ActivatedRoute) {
    this.baseUrlQrData = environment.baseUrlQrData;
    this.infobatch = [];
  }

  async ngOnInit() {
    await this.getProfile();
    this.route.paramMap.subscribe((pm: any) => {
      this.clean();
      this.id = pm.get("id");
      this.update();
    })
  }

  async getProfile() {
        if (this.keycloakService.isLoggedIn()) {
      let userProfile: any = await this.keycloakService.loadUserProfile();
      this.companyLogged = userProfile['attributes']['company'][0];
      console.log("Company logged ", this.companyLogged);
    }
  }

  clean() {
    this.batch = null;
    this.batchTruck = null;
    this.id = null;
    this.isMine = false;
    this.companyLogged = null;
    this.infobatch = [];
  }

  update() {
    if (this.id) {
      this.batchService.getOne(this.id).subscribe({
        next: async (res: any) => {
          console.log("RES", res)
          this.batch = res;

          if (!this.companyLogged) {
            await this.getProfile();
          }

    this.transferService.getAllByBatchId(this.batch.batchId).subscribe({
      next: (res: any) => {
        res.map((tx: any) => {
          let formatDate = tx.transferDateStart;
          let formatDateStr = ""
          if (tx.transferDateStart) {
            formatDate = new Date(tx.transferDateStart);
            formatDateStr = formatDate.toLocaleString();
          }
          this.infobatch.push({
            date: formatDateStr,
            from: tx.companySenderUsername,
            to: tx.companyRecipientUsername,
            batchId: tx.newBatchId,
          });
        });
        console.log(res);
      },
      error: (err: any) => {
        console.log(err);
      },
    });


          this.qrData = this.baseUrlQrData + "/" +  this.companyLogged  + "/" + this.batch.batchId
        },
        error: (err: any) => {
          console.log("Errore dal server", err)
        }
      })

      this.batchService.getTrack(this.id).subscribe({
        next: (res: any) => {
          console.log("RES Truck", res)
          this.batchTruck = res;
        },
        error: (err: any) => {
          console.log("Errore dal server", err)
        }
      })
    }
  }
}
