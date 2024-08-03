import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';
import { TruckService } from '../../services/truck.service';

@Component({
  selector: 'app-truck-list',
  standalone: true,
  imports: [TableModule],
  templateUrl: './truck-list.component.html',
  styleUrl: './truck-list.component.scss'
})
export class TruckListComponent implements OnInit {

  trucks!: any;


  constructor(private truckService: TruckService) {}
  ngOnInit(): void {
    this.truckService.getAll().subscribe({
      next: (res: any) => {
        this.trucks = res;
      },
      error: (err: any) => {

      }
    })
  }

}
