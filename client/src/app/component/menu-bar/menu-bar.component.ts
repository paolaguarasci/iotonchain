import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { AvatarModule } from 'primeng/avatar';
import { BadgeModule } from 'primeng/badge';
import { InputTextModule } from 'primeng/inputtext';
import { MenubarModule } from 'primeng/menubar';
import { RippleModule } from 'primeng/ripple';
import { TieredMenuModule } from 'primeng/tieredmenu';
@Component({
  selector: 'app-menu-bar',
  standalone: true,
  imports: [
    MenubarModule,
    BadgeModule,
    AvatarModule,
    InputTextModule,
    RippleModule,
    CommonModule,
    TieredMenuModule,
    CommonModule,
  ],

  templateUrl: './menu-bar.component.html',
  styleUrl: './menu-bar.component.scss',
})
export class MenuBarComponent implements OnInit {
  items: MenuItem[] | undefined;
  ngOnInit() {
    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        route: '/',
      },
      {
        label: 'Manage',
        icon: 'pi pi-search',
        items: [
          {
            label: 'Batch',
            icon: 'pi pi-bolt',
            route: '/product',
          },
          {
            label: 'Product type',
            icon: 'pi pi-server',
            route: '/product-type',
          },
          {
            label: 'Production Step',
            icon: 'pi pi-pencil',
            route: '/production-step',
          },
          {
            label: 'Document',
            icon: 'pi pi-palette',
            items: [
              {
                label: 'Analisys',
                icon: 'pi pi-palette',
                route: '/analisys',
              },
              {
                label: 'Certificate',
                icon: 'pi pi-palette',
                route: '/certificate',
              },
            ],
          },
        ],
      },
      {
        label: 'Traceability',
        icon: 'pi pi-search',
        items: [
          {
            label: 'Batch',
            icon: 'pi pi-bolt',
          },
          {
            label: 'Product type',
            icon: 'pi pi-server',
          },
          {
            label: 'Production Step',
            icon: 'pi pi-pencil',
          },
          {
            label: 'Templates',
            icon: 'pi pi-palette',
            items: [
              {
                label: 'Apollo',
                icon: 'pi pi-palette',
              },
              {
                label: 'Ultima',
                icon: 'pi pi-palette',
              },
            ],
          },
        ],
      },
      // {
      //   label: 'Contact',
      //   icon: 'pi pi-envelope',
      // },
    ];
  }
}

/*

  {
    path: 'product',
    component: ProductListComponent,
  },
  {
    path: 'product-type',
    component: ProductTypeListComponent,
  },
  {
    path: 'production-step',
    component: CreateStepsComponent,
  },

*/
