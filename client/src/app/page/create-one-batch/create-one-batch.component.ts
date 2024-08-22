import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { ProductTypeService } from '../../services/product-type.service';
import { MessageService } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';

import { PickListModule } from 'primeng/picklist';
import { CommonModule } from '@angular/common';

import { concat, flatMap, flatMapDepth } from 'lodash'
import { DocumentService } from '../../services/document.service';
import { MultiSelectModule } from 'primeng/multiselect';
import { ButtonModule } from 'primeng/button';
import { CompanyBatchService } from '../../services/company-batch.service';
import { ToastModule } from 'primeng/toast';
@Component({
  selector: 'app-create-one-batch',
  standalone: true,
  imports: [FormsModule, DropdownModule, PickListModule, CommonModule, MultiSelectModule, ButtonModule, ToastModule],
  providers: [MessageService],
  templateUrl: './create-one-batch.component.html',
  styleUrl: './create-one-batch.component.scss'
})
export class CreateOneBatchComponent implements OnInit {
  pTypeId !: any;
  productType !: any;
  selectedProductType !: any;
  selectedProductTypeFull !: any;
  productionSteps !: any;
  documents: any[] = [];
  selectedDocuments: any[] = [];
  selectedProductionSteps !: any;
  selectedRecipe: any[] = [];
  selectedProductProcessFull !: any;
  selectedSteps: any[] = []
  selectedBatchId!: any;
  selectedDescription !: any;
  selectedQuantity !: any;


  constructor(
    private productionTypeService: ProductTypeService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private router: Router,
    private companyBatchService: CompanyBatchService,
    private documentService: DocumentService
  ) { }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((pmap: any) => {
      this.clean()
      this.getDocs();
      this.pTypeId = pmap.get("ptypeid")
      this.productionTypeService.getAll().subscribe({
        next: (res: any) => {
          this.productType = res.map((pt: any) => {
            return {
              name: pt.name,
              code: pt.id
            }
          });

          if (this.pTypeId) {
            this.selectedProductType = this.productType.filter((pt: any) => pt.code === this.pTypeId)[0]

            this.selectedProductTypeFull = res.filter((pt: any) => pt.id === this.pTypeId)[0]

            let tmpmap: any = [];
            let tmpmap2: any = [];

/*             if (res.filter((pt: any) => pt.id === this.pTypeId)[0].recipe) {
              tmpmap = res.filter((pt: any) => pt.id === this.pTypeId)[0].recipe.recipeRow
                .map((ingredient: any) => {
                  console.log("ingredient ", ingredient)
                  return ingredient.product.productionProcess.steps.map((step: any) => {
                    return {
                      productTypeId: ingredient.product.id,
                      productTypeName: ingredient.product.name,
                      stepId: step.id,
                      stepName: step.name,
                      stepDesc: step.description
                    }
                  })
                })
            }
 */
            tmpmap2 = res.filter((pt: any) => pt.id === this.pTypeId)[0].productionProcess.steps.map((step: any) => {
              return {
                productTypeId: res.filter((pt: any) => pt.id === this.pTypeId)[0].id,
                productTypeName: res.filter((pt: any) => pt.id === this.pTypeId)[0].name,
                stepId: step.id,
                stepName: step.name,
                stepDesc: step.description
              }
            })



            this.selectedProductProcessFull = flatMapDepth(concat(tmpmap, tmpmap2))

          }

        },
        error: (err: any) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: err })
        }
      })

    })

  }

  updateSelectedPType() {

    this.router.navigate(['/product/new'], { queryParams: { ptypeid: this.selectedProductType.code } });
  }


  getDocs() {
    this.documentService.getAllDocuments().subscribe({
      next: (res: any) => {
        let that = this;
        res.map((doc: any) => {
          that.documents.push({
            name: doc.path,
            code: doc.id
          })
        })
      },
      error: (err: any) => {

      }
    })
  }

  clean() {
    this.pTypeId = null;
    this.productType = null;
    this.selectedProductType = null;
    this.productionSteps = null;
    this.selectedProductionSteps = null;
    this.selectedRecipe = [];
    this.selectedProductProcessFull = null;
    this.selectedSteps = [];
    this.documents = [];
    this.selectedDocuments = []
    this.selectedQuantity = 0;
    this.selectedBatchId = null;
    this.selectedDescription = null;

  }

  save() {
    let toSave = {
      batchId: this.selectedBatchId,
      description: this.selectedDescription,
      documents: this.selectedDocuments.map((doc: any) => doc.code),
      productionSteps: this.selectedSteps.map((step: any, index: any) => {
        return { id: step.stepId, position: index }
      }),
      ingredients: this.selectedRecipe.map((row: any) => row.id),
      quantity: this.selectedQuantity,
      unity: this.selectedProductTypeFull.unity,
      productTypeID: this.selectedProductTypeFull.id
    }
    // console.log(" --- ", toSave)
    console.log(this.selectedDocuments)
    this.companyBatchService.produceBatch(toSave).subscribe({
      next: (res: any) => {
        console.log("Creazione!", res)
        this.messageService.add({ severity: 'success', summary: 'OK', detail: "" })
      },
      error: (err: any) => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: err.error.message })
      },
    })
  }
}
