import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CompanyBatchService } from '../../services/company-batch.service';
import { faWheatAwn } from '@fortawesome/free-solid-svg-icons';
import { NotarizationService } from '../../services/notarization.service';
import { sortBy } from 'lodash';
import { KeycloakService } from 'keycloak-angular';
import { TimelineModule } from 'primeng/timeline';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonModule } from '@angular/common';
import { PublicService } from '../../services/public.service';

@Component({
  selector: 'app-trace-one-batch-public',
  standalone: true,
  imports: [TimelineModule, CardModule, ButtonModule, FontAwesomeModule, CommonModule],
  templateUrl: './trace-one-batch-public.component.html',
  styleUrl: './trace-one-batch-public.component.scss'
})
export class TraceOneBatchPublicComponent implements OnInit {
  id !: any;
  productionSteps: any[] = []
  companyLogged !: any;
  faWheatAwn = faWheatAwn
  company !: any;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private publicService: PublicService,
    private notarizeService: NotarizationService,
    private keyclokService: KeycloakService
  ) { }

  private findStep(arr: any, step_id: any) {
    let keys = Object.keys(arr);
    if (keys.includes("global")) {
      keys.splice(keys.indexOf("global"), 1);
    }
    let res: any;
    keys.forEach((key: any) => {
      console.log("Cerco", step_id, arr[key])
      arr[key].map((s: any) => {
        if (s.step_id === step_id) {
          res = key;
        }
      })
    })

    console.log(arr[res])
    if (arr[res] && arr[res].length > 0) {
      return arr[res][0];
    }
    return null;
  }

  async ngOnInit() {

    if (this.keyclokService.isLoggedIn()) {
      let userProfile: any = await this.keyclokService.loadUserProfile();
      this.companyLogged = userProfile['attributes']['company'][0];
      console.log("Company logged ", this.companyLogged);
    }

    this.route.paramMap.subscribe((param: any) => {
      this.id = param.get("batchid");
      this.company = param.get("company");
      // this.makeTemplate();
      this.publicService.getOneTrackInfo(this.company, this.id).subscribe({
        next: (res: any) => {
          console.log("RES ", res)
          this.productionSteps = []
          res.global.steps.map((step: any) => {
            let batch = this.findStep(res, step.id);
            let x = {
              id: step.id,
              name: step.name,
              description: step.description,
              batch_id: "",
              company: "",
              notarization: step.notarize ?? null,
              icon: faWheatAwn,
              position: step.position
            }

            if (batch) {
              x.batch_id = batch.id
              x.company = batch.company
              // x.notarization = batch.notarize
            }

            this.productionSteps.push(x)
          });

          this.productionSteps = sortBy(this.productionSteps, "position")
          console.log("Production steps ", res)
        },
        error: (err: any) => {
          console.log(err)
        }
      })
    })
  }

  makeTemplate() {
    this.productionSteps.push({
      status: 'Raccolta olive',
      date: '03/12/2024',
      icon: 'pi pi-shopping-cart',
      color: '#2DB1B2',
      company: "azienda 9",
      image: 'game-controller.jpg'
    })
    this.productionSteps.push({
      status: 'Frantura olive',
      date: '27/07/2024',
      icon: 'pi pi-shopping-cart',
      color: '#260732',
      company: "azienda 9",
      image: 'game-controller.jpg'
    })
    this.productionSteps.push({
      status: 'Raccolta aglio',
      date: '24/06/2024',
      icon: 'pi pi-shopping-cart',
      color: '#E7CF7E',
      company: "azienda H",
      image: 'game-controller.jpg'
    })
    this.productionSteps.push({
      status: 'Raccolta basilico',
      date: '17/10/2024',
      icon: 'pi pi-shopping-cart',
      color: '#84D170',
      company: "azienda v",
      image: 'game-controller.jpg'
    })
    this.productionSteps.push({
      status: 'Produzione pesto',
      date: '11/07/2024',
      icon: 'pi pi-shopping-cart',
      color: '#23EDB6',
      company: "azienda J",
      image: 'game-controller.jpg'
    })
  }

  notarize(step: any) {
    this.notarizeService.notarizeOneStep(step.id).subscribe({
      next: (res: any) => {
        console.log("Notarize ", res)
      },
      error: (err: any) => {

      }
    })
  }

}
