import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TimelineModule } from 'primeng/timeline';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { CompanyBatchService } from '../../services/company-batch.service';
import { faWheatAwn } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
@Component({
  selector: 'app-trace-one-batch',
  standalone: true,
  imports: [TimelineModule, CardModule, ButtonModule, FontAwesomeModule],
  templateUrl: './trace-one-batch.component.html',
  styleUrl: './trace-one-batch.component.scss'
})
export class TraceOneBatchComponent implements OnInit {
  id !: any;
  productionSteps: any[] = []

  faWheatAwn = faWheatAwn

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private companyBatchService: CompanyBatchService
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
    return arr[res][0];
  }

  ngOnInit() {
    this.route.paramMap.subscribe((param: any) => {
      this.id = param.get("id");
      // this.makeTemplate();
      this.companyBatchService.getTrack(this.id).subscribe({
        next: (res: any) => {
          this.productionSteps = []
          res.global.steps.map((step: any) => {
            let batch = this.findStep(res, step.id);
            let x = {
              name: step.name,
              description: step.description,
              batch_id: "",
              company: "",
              notarization: "",
              icon: faWheatAwn
            }

            if (batch) {
              x.batch_id = batch.id
              x.company = batch.company
              x.notarization = batch.notarizatio
            }

            this.productionSteps.push(x)
          });
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


}
