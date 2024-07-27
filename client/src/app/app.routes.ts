import { Routes } from '@angular/router';
import { CreateStepsComponent } from './page/create-steps/create-steps.component';
import { ComapnyBatchListComponent } from './page/batch-list/product-list.component';
import { ProductTypeListComponent } from './page/product-type-list/product-type-list.component';
import { DashboardComponent } from './page/dashboard/dashboard.component';
import { DocumentsListComponent } from './page/documents-list/documents-list.component';
import { LandingChainComponent } from './page/landing-chain/landing-chain.component';

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
    path: 'document',
    component: DocumentsListComponent,
  },
  {
    path: 'product-type',
    component: ProductTypeListComponent,
  },
    {
      path: 'production-step',
      component: CreateStepsComponent,
    },
    {
      path: 'chain/:id',
      component: LandingChainComponent,
    },
];
