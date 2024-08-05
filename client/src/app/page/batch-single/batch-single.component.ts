import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { CompanyBatchService } from '../../services/company-batch.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-batch-single',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './batch-single.component.html',
  styleUrl: './batch-single.component.scss'
})
export class BatchSingleComponent implements OnInit {

  batch !: any;
  batchTruck !: any;
  id!: any;
  isMine !: any;
  companyLogged !: any;

  constructor(private keycloakService: KeycloakService, private batchService: CompanyBatchService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((pm: any) => {
      this.clean();
      this.id = pm.get("id");
      this.update();
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
