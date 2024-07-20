import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { PickListModule } from 'primeng/picklist';
import { CompanyBatchService } from '../../services/company-batch.service';

@Component({
  selector: 'app-create-steps',
  standalone: true,
  imports: [PickListModule],
  providers: [CompanyBatchService],
  templateUrl: './create-steps.component.html',
  styleUrl: './create-steps.component.scss',
})
export class CreateStepsComponent implements OnInit {
  sourceProducts!: any[];
  targetProducts!: any[];
  sourceProductsOriginal!: any[];
  constructor(
    private productService: CompanyBatchService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    // this.productService.getAll().subscribe((products: any) => {
    //   this.sourceProducts = products;
    // });

    this.sourceProducts = [
      {
        id: '1001',
        code: 'f230fh0g3',
        name: 'Bamboo Watch 1',
        description: 'Product Description',
        image: 'bamboo-watch.jpg',
        price: 65,
        category: 'Accessories',
        quantity: 24,
        inventoryStatus: 'INSTOCK',
        rating: 5,
      },
      {
        id: '1002',
        code: 'f230fh0g3',
        name: 'Bamboo Watch 2',
        description: 'Product Description',
        image: 'bamboo-watch.jpg',
        price: 65,
        category: 'Accessories',
        quantity: 24,
        inventoryStatus: 'INSTOCK',
        rating: 5,
      },
      {
        id: '1003',
        code: 'f230fh0g3',
        name: 'Bamboo Watch 3',
        description: 'Product Description',
        image: 'bamboo-watch.jpg',
        price: 65,
        category: 'Accessories',
        quantity: 24,
        inventoryStatus: 'INSTOCK',
        rating: 5,
      },
      {
        id: '1004',
        code: 'f230fh0g3',
        name: 'Bamboo Watch 4',
        description: 'Product Description',
        image: 'bamboo-watch.jpg',
        price: 65,
        category: 'Accessories',
        quantity: 24,
        inventoryStatus: 'INSTOCK',
        rating: 5,
      },
      {
        id: '1005',
        code: 'f230fh0g3',
        name: 'Bamboo Watch 5',
        description: 'Product Description',
        image: 'bamboo-watch.jpg',
        price: 65,
        category: 'Accessories',
        quantity: 24,
        inventoryStatus: 'INSTOCK',
        rating: 5,
      },
    ];

    this.sourceProductsOriginal = [
      {
        id: '1001',
        code: 'f230fh0g3',
        name: 'Bamboo Watch 1',
        description: 'Product Description',
        image: 'bamboo-watch.jpg',
        price: 65,
        category: 'Accessories',
        quantity: 24,
        inventoryStatus: 'INSTOCK',
        rating: 5,
      },
      {
        id: '1002',
        code: 'f230fh0g3',
        name: 'Bamboo Watch 2',
        description: 'Product Description',
        image: 'bamboo-watch.jpg',
        price: 65,
        category: 'Accessories',
        quantity: 24,
        inventoryStatus: 'INSTOCK',
        rating: 5,
      },
      {
        id: '1003',
        code: 'f230fh0g3',
        name: 'Bamboo Watch 3',
        description: 'Product Description',
        image: 'bamboo-watch.jpg',
        price: 65,
        category: 'Accessories',
        quantity: 24,
        inventoryStatus: 'INSTOCK',
        rating: 5,
      },
      {
        id: '1004',
        code: 'f230fh0g3',
        name: 'Bamboo Watch 4',
        description: 'Product Description',
        image: 'bamboo-watch.jpg',
        price: 65,
        category: 'Accessories',
        quantity: 24,
        inventoryStatus: 'INSTOCK',
        rating: 5,
      },
      {
        id: '1005',
        code: 'f230fh0g3',
        name: 'Bamboo Watch 5',
        description: 'Product Description',
        image: 'bamboo-watch.jpg',
        price: 65,
        category: 'Accessories',
        quantity: 24,
        inventoryStatus: 'INSTOCK',
        rating: 5,
      },
    ];

    this.targetProducts = [];
  }
}
