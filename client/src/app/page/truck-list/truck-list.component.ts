import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';
import { TruckService } from '../../services/truck.service';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-truck-list',
  standalone: true,
  imports: [TableModule, ButtonModule],
  templateUrl: './truck-list.component.html',
  styleUrl: './truck-list.component.scss'
})
export class TruckListComponent implements OnInit {

  trucks!: any;


  constructor(private truckService: TruckService) { }
  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.truckService.getAll().subscribe({
      next: (res: any) => {
        this.trucks = res;
        console.log("TRUCK RES GET DATA ", res)
      },
      error: (err: any) => {

      }
    })
  }

  update() {
    this.truckService.updateAll().subscribe({
      next: (res: any) => {
        this.getData();
      },
      error: (err: any) => { }
    })
  }
}
