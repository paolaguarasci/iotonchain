import { Routes } from '@angular/router';
import { CreateStepsComponent } from './page/create-steps/create-steps.component';
import { ComapnyBatchListComponent } from './page/batch-list/product-list.component';
import { ProductTypeListComponent } from './page/product-type-list/product-type-list.component';
import { DashboardComponent } from './page/dashboard/dashboard.component';

export const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
  },
  {
    path: 'product',
    component: ComapnyBatchListComponent,
  },
  {
    path: 'product-type',
    component: ProductTypeListComponent,
  },
  {
    path: 'production-step',
    component: CreateStepsComponent,
  },
];
