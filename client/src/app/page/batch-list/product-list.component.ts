import { Component, OnInit } from '@angular/core';
import { CompanyBatchService } from '../../services/company-batch.service';

import { SelectItem } from 'primeng/api';
import { DataViewModule } from 'primeng/dataview';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { CommonModule } from '@angular/common';
import { DropdownModule } from 'primeng/dropdown';
@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    DataViewModule,
    ButtonModule,
    TagModule,
    CommonModule,
    DropdownModule,
  ],
  providers: [CompanyBatchService],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss',
})
export class ComapnyBatchListComponent implements OnInit {
  products: any[] = [];
  constructor(private productService: CompanyBatchService) {}
  ngOnInit(): void {
    this.productService.getAll().subscribe((res: any) => {
      this.products = res;
    });




    // this.products = [
    //   {
    //     id: '1000',
    //     code: 'f230fh0g3',
    //     name: 'Bamboo Watch',
    //     description: 'Product Description',
    //     image: 'bamboo-watch.jpg',
    //     price: 65,
    //     category: 'Accessories',
    //     quantity: 24,
    //     inventoryStatus: 'INSTOCK',
    //     rating: 5
    // }
    // ]
  }
  getSeverity(items: any) {
    return null;
  }
}
