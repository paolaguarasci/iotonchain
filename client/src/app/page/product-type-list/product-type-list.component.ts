import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';

import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { RippleModule } from 'primeng/ripple';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { CommonModule } from '@angular/common';
import { FileUploadModule } from 'primeng/fileupload';
import { DropdownModule } from 'primeng/dropdown';
import { TagModule } from 'primeng/tag';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RatingModule } from 'primeng/rating';
import { FormsModule } from '@angular/forms';
import { InputNumberModule } from 'primeng/inputnumber';
import { ListboxModule } from 'primeng/listbox';
import { OverlayPanel, OverlayPanelModule } from 'primeng/overlaypanel';
import { ProductTypeService } from '../../services/product-type.service';
import { CompanyBatchService } from '../../services/company-batch.service';
@Component({
  selector: 'app-product-type-list',
  standalone: true,
  imports: [
    CommonModule,
    ListboxModule,
    TableModule,
    DialogModule,
    RippleModule,
    ButtonModule,
    ToastModule,
    ToolbarModule,
    ConfirmDialogModule,
    InputTextModule,
    InputTextareaModule,
    CommonModule,
    FileUploadModule,
    DropdownModule,
    TagModule,
    RadioButtonModule,
    RatingModule,
    InputTextModule,
    FormsModule,
    InputNumberModule,
    DialogModule,
    ButtonModule,
    InputTextModule,
    TableModule,
    ToastModule,
    CommonModule,
    TagModule,
    DropdownModule,
    ButtonModule,
    InputTextModule,
    OverlayPanelModule, ToastModule, TableModule, ButtonModule
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './product-type-list.component.html',
  styleUrl: './product-type-list.component.scss',
})
export class ProductTypeListComponent implements OnInit {
  // productTypeList!: any[];
  selectedRecipe!: any;
  productDialog: boolean = false;
  products!: any[];
  product!: any;
  selectedProducts!: any[] | null;
  submitted: boolean = false;
  statuses!: any[];
  inforecipe !: any;
  selectedProductToReciperRow!: any;
  selectedProductToReciperRowquantity!: any;
  selectedBatchToProduce!: any;
  showInfoDialog = false;
  newBatchQty!: any;
  newBatchBid!: any;
  unityOfMeasuremente = [
    { name: 'Liter', code: 'lt' },
    { name: 'Kilograms', code: 'kg' },
    { name: 'Unit', code: 'unit' },
  ];
  newBatch!: any;
  createBatchDialogVisible: boolean = false;


  @ViewChild('recipedetails', { static: false }) divHello!: OverlayPanel;

  constructor(
    private productTypeService: ProductTypeService,
    private batchService: CompanyBatchService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {

this.update();
    this.statuses = [];
  }
showRecipeInfo(product: any, $event: any) {
  this.divHello.show($event);
  this.inforecipe = product.recipe.recipeRow
}
  update() {
    this.productTypeService.getAll().subscribe({
      next: (res: any) => (this.products = res),
      error: (err: any) => console.log(err),
    });
  }

  openNew() {
    this.product = {};
    this.submitted = false;
    this.productDialog = true;
  }

  deleteSelectedProducts() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected products?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.products = this.products.filter(
          (val) => !this.selectedProducts?.includes(val)
        );
        this.selectedProducts = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Products Deleted',
          life: 3000,
        });
      },
    });
  }

  showDialogCreateBatch(ptypeSelected: any) {
    this.createBatchDialogVisible = true;
    this.selectedBatchToProduce = ptypeSelected;
  }

  editProduct(product: any) {
    this.product = { ...product };
    this.productDialog = true;
  }

  deleteProduct(product: any) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + product.name + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.products = this.products.filter((val) => val.id !== product.id);
        this.product = {};
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Product Deleted',
          life: 3000,
        });
      },
    });
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
  }
  saveProduct() {
    this.submitted = true;
    this.product.state = 'FINAL';
    this.product.unity = this.product.unity.code;
    this.productTypeService.createOne(this.product).subscribe({
      next: (res: any) => {
        this.update();
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Product Type Created',
          life: 3000,
        });
      },
      error: (err: any) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Creation error',
          life: 3000,
        });
      },
    });

    this.products = [...this.products];
    this.productDialog = false;
    this.product = {};
  }

  findIndexById(id: string): number {
    let index = -1;
    for (let i = 0; i < this.products.length; i++) {
      if (this.products[i].id === id) {
        index = i;
        break;
      }
    }

    return index;
  }

  createId(): string {
    let id = '';
    var chars =
      'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (var i = 0; i < 5; i++) {
      id += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return id;
  }

  getSeverity(status: string) {
    switch (status) {
      case 'INSTOCK':
        return 'success';
      case 'LOWSTOCK':
        return 'warning';
      case 'OUTOFSTOCK':
        return 'danger';
    }
    return 'warning';
  }

  deleteRepiceRow(prod: any, row: any) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + prod.name + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        let product = this.products.filter((p) => p.id === prod.id);
        product[0].recipe.recipeRow = product[0].recipe.recipeRow.filter(
          (r: any) => r.id !== row.id
        );

        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Product Deleted',
          life: 3000,
        });
      },
    });
  }

  hadleProduceBatchFromProductType() {
    this.newBatch = {
      batchId: this.newBatchBid,
      quantity: parseInt(this.newBatchQty, 10),
      unity: this.selectedBatchToProduce.unity,
      productTypeID: this.selectedBatchToProduce.id,
    };
    this.batchService.produceBatch(this.newBatch).subscribe({
      next: (res: any) => {
        this.createBatchDialogVisible = false;

        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Batch addedd',
          life: 3000,
        });
      },
      error: (err: any) => {
        console.log(err);
        this.messageService.add({
          severity: 'warning',
          summary: 'Error',
          detail: 'Error creating batch',
          life: 3000,
        });
      },
    });
  }

  onRowEditInit(reciperow: any) {
    // this.clonedProducts[product.id as string] = { ...product };
  }

  onRowEditSave(reciperow: any) {
    // if (product.price > 0) {
    //     delete this.clonedProducts[product.id as string];
    //     this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Product is updated' });
    // } else {
    //     this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Invalid Price' });
    // }
  }

  onRowEditCancel(reciperow: any, index: number) {
    // this.products[index] = this.clonedProducts[product.id as string];
    // delete this.clonedProducts[product.id as string];
  }

  onRowCancel(product: any, index: number) {
    product.recipe.recipeRow.splice(index, 1);
    if (product.recipe.recipeRow.length == 0) {
      delete product.recipe;
    }
  }

  addRecipe(product: any) {
    if (!product.recipe) {
      product.recipe = {
        note: null,
        recipeRow: [
          {
            unity: '%',
            quantity: 0,
            note: null,
            product: null,
          },
        ],
      };
    }
  }

  addRecipeRow(product: any) {
    if (
      product.recipe &&
      product.recipe.recipeRow &&
      (product.recipe.recipeRow.length == 0 ||
        (product.recipe.recipeRow.length > 0 &&
          product.recipe.recipeRow[product.recipe.recipeRow.length - 1]
            .product != null &&
          product.recipe.recipeRow[product.recipe.recipeRow.length - 1]
            .quantity != null))
    ) {
      product.recipe.recipeRow.push({
        unity: '%',
        quantity: 0,
        note: null,
        product: null,
      });
    }
  }
}
