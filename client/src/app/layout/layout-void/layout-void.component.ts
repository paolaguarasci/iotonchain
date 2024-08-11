import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-layout-void',
  standalone: true,
  imports:  [
    RouterModule, CommonModule
  ],
  templateUrl: './layout-void.component.html',
  styleUrl: './layout-void.component.scss'
})
export class LayoutVoidComponent {

}

