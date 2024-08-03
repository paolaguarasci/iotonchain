import { Component, OnInit } from '@angular/core';
import { TransportService } from '../../services/transport.service';
import { CommonModule } from '@angular/common';
import { TableModule, TableRowCollapseEvent, TableRowExpandEvent } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { RatingModule } from 'primeng/rating';
import { ButtonModule } from 'primeng/button';
import { TruckService } from '../../services/truck.service';

@Component({
  selector: 'app-transport',
  standalone: true,
  imports: [CommonModule, TableModule, TagModule, ToastModule, RatingModule, ButtonModule],
  templateUrl: './transport.component.html',
  styleUrl: './transport.component.scss'
})
export class TransportComponent implements OnInit {

  transports !: any;
  expandedRows = {};

  constructor(private transportService: TransportService, private truckService: TruckService) {

  }

  ngOnInit() {
    this, this.transportService.getAll().subscribe((res: any) => {
      this.transports = res;
    })
  }

  expandAll() {
    this.expandedRows = this.transports.reduce((acc: any, p: any) => (acc[p.id] = true) && acc, {});
  }

  collapseAll() {
    this.expandedRows = {};
  }

  onRowExpand(event: TableRowExpandEvent) {
    if (!event.data.truckDetails) {
      this.truckService.getTruckByTransportId(event.data.id).subscribe({
        next: (res: any) => {
          event.data.truckDetails = []
          event.data.truckDetails.push(res);
        },
        error: (err: any) => {

        },
      })
    }
  }

  onRowCollapse(event: TableRowCollapseEvent) {
    // this.messageService.add({ severity: 'success', summary: 'Product Collapsed', detail: event.data.name, life: 3000 });
  }

}
