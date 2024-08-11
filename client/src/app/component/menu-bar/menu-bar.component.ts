import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
import { MenuItem } from 'primeng/api';
import { AvatarModule } from 'primeng/avatar';
import { BadgeModule } from 'primeng/badge';
import { ButtonModule } from 'primeng/button';
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
    ButtonModule,
  ],
  templateUrl: './menu-bar.component.html',
  styleUrl: './menu-bar.component.scss',
})
export class MenuBarComponent implements OnInit {
  isLogged = false;
  username = '';
  profile!: KeycloakProfile;
  items: MenuItem[] | undefined;

  constructor(private readonly keycloakService: KeycloakService) { }

  async ngOnInit() {
    this.isLogged = await this.keycloakService.isLoggedIn();
    if (this.isLogged) {
      this.profile = await this.keycloakService.loadUserProfile();
      console.log(this.profile);
    }
    this.items = [
      {
        label: 'Dashboard',
        icon: 'pi pi-home',
        route: '/dash',
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
          // {
          //   label: 'Production Step',
          //   icon: 'pi pi-pencil',
          //   route: '/production-step',
          // },
          {
            label: 'Document',
            icon: 'pi pi-palette',
            route: '/document',
            items: [
              // {
              //   label: 'Analisys',
              //   icon: 'pi pi-palette',
              //   route: '/analisys',
              // },
              // {
              //   label: 'Certificate',
              //   icon: 'pi pi-palette',
              //   route: '/certificate',
              // },
            ],
          },
        ],
      },
      {
        label: 'Traceability',
        icon: 'pi pi-search',
        items: [
          {
            label: 'Trucks',
            icon: 'pi pi-server',
            route: "/truck-list",
          },
          {
            label: 'Transport',
            icon: 'pi pi-bolt',
            route: '/transport',
          },
          // {
          //   label: 'Production Step',
          //   icon: 'pi pi-pencil',
          //   route: '/document',
          // },
          // {
          //   label: 'Templates',
          //   icon: 'pi pi-palette',
          //   items: [
          //     {
          //       label: 'Apollo',
          //       icon: 'pi pi-palette',
          //     },
          //     {
          //       label: 'Ultima',
          //       icon: 'pi pi-palette',
          //     },
          //   ],
          // },
        ],
      },

      {
        label: 'Chain',
        icon: 'pi pi-search',
        items: [
          {
            label: 'Notarization',
            icon: 'pi pi-server',
            route: "/notarize-list",
          },
          {
            label: 'Transfer',
            icon: 'pi pi-server',
            route: "/transfert-list",
          }
        ]
      }
    ];
  }

  handleLotout() {
    this.keycloakService.logout();
  }
  handleLogin() {
    this.keycloakService.login();
  }
}
