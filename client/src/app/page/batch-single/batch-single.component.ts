import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { CompanyBatchService } from '../../services/company-batch.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { QRCodeModule } from 'angularx-qrcode';
import { environment } from '../../../environments/environment';
@Component({
  selector: 'app-batch-single',
  standalone: true,
  imports: [CommonModule, ButtonModule, QRCodeModule],
  templateUrl: './batch-single.component.html',
  styleUrl: './batch-single.component.scss'
})
export class BatchSingleComponent implements OnInit {

  baseUrlQrData !: any;
  batch !: any;
  batchTruck !: any;
  id!: any;
  isMine !: any;
  companyLogged !: any;
  qrData !: any;

  constructor(private keycloakService: KeycloakService, private batchService: CompanyBatchService, private router: Router, private route: ActivatedRoute) {
    this.baseUrlQrData = environment.baseUrlQrData;
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((pm: any) => {
      this.clean();
      this.id = pm.get("id");
      this.update();
      this.qrData = this.baseUrlQrData + "/" + this.id
    })
  }

  clean() {
    this.batch = null;
    this.batchTruck = null;
    this.id = null;
    this.isMine = false;
    this.companyLogged = null;
  }

  update() {
    if (this.id) {
      this.batchService.getOne(this.id).subscribe({
        next: (res: any) => {
          console.log("RES", res)
          this.batch = res;
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
