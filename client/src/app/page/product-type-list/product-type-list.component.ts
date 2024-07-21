import { Component, OnInit } from '@angular/core';
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
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './product-type-list.component.html',
  styleUrl: './product-type-list.component.scss',
})
export class ProductTypeListComponent implements OnInit {
  // productTypeList!: any[];

  productDialog: boolean = false;
  products!: any[];
  product!: any;
  selectedProducts!: any[] | null;
  submitted: boolean = false;
  statuses!: any[];

  selectedProductToReciperRow!: any;
  selectedProductToReciperRowquantity!: any;

  newBatch!: any;

  constructor(
    private productTypeService: ProductTypeService,
    private batchService: CompanyBatchService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.productTypeService.getAll().subscribe({
      next: (res: any) => (this.products = res),
      error: (err: any) => console.log(err),
    });

    this.statuses = [];
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

    if (this.product.name?.trim()) {
      if (this.product.id) {
        this.products[this.findIndexById(this.product.id)] = this.product;



        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Product Updated',
          life: 3000,
        });
      } else {
        this.product.id = this.createId();
        this.product.image = 'product-placeholder.svg';


        this.products.push(this.product);
        let newPType = {
         name: this.product.name,
         unity: this.product.unity ?? "kg",
         state: "",
        }
        this.productTypeService.createOne(this.product).subscribe({
          next: (res: any) => {
            
          },
          error: (err: any) => {}
        });

        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Product Created',
          life: 3000,
        });
      }

      this.products = [...this.products];
      this.productDialog = false;
      this.product = {};
    }
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

  addRecipeRow(product: any) {
    let prod = this.products.filter((row: any) => {
      return row.id === product.id;
    });
    if (prod[0].recipe) {
      prod[0].recipe.recipeRow.push({
        unity: '%',
        quantity: this.selectedProductToReciperRowquantity,
        note: null,
        product: this.selectedProductToReciperRow,
      });
    } else {
      prod[0].recipe = {
        note: null,
        recipeRow: [],
      };
    }

    this.selectedProductToReciperRowquantity = null;
    this.selectedProductToReciperRow = null;
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

  hadleProduceBatchFromProductType(productType: any) {
    this.newBatch = {
      batchId: 'nuovo-batch',
      quantity: 100,
      unity: 'kg',
      productTypeID: productType.id,
    };
    this.batchService.produceBatch(this.newBatch).subscribe({
      next: (res: any) => {
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
}
