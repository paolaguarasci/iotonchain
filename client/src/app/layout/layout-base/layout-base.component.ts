import { CommonModule } from '@angular/common';
import { Component, OnDestroy } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MenuBarComponent } from '../../component/menu-bar/menu-bar.component';
@Component({
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MenuBarComponent
  ],
  selector: 'app-layout-base',
  templateUrl: './layout-base.component.html',
  styleUrl: './layout-base.component.scss'
})
export class LayoutBaseComponent implements OnDestroy {


  ngOnDestroy() {

  }

}



