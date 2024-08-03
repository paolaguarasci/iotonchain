import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule, TableRowCollapseEvent, TableRowExpandEvent} from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { RatingModule } from 'primeng/rating';
import { ButtonModule } from 'primeng/button';
import { NotarizationService } from '../../services/notarization.service';

@Component({
  selector: 'app-notarize-list',
  standalone: true,
  imports: [CommonModule, TableModule, TagModule, ToastModule, RatingModule, ButtonModule],
  templateUrl: './notarize-list.component.html',
  styleUrl: './notarize-list.component.scss'
})
export class NotarizeListComponent implements OnInit{
  notarizations !: any;
  expandedRows = {};

  constructor(private notarizeService: NotarizationService) {

  }

  ngOnInit() {
    this, this.notarizeService.getAll().subscribe((res: any) => {
      this.notarizations = res;
    })
  }

  expandAll() {
    this.expandedRows = this.notarizations.reduce((acc: any, p: any) => (acc[p.id] = true) && acc, {});
  }

  collapseAll() {
    this.expandedRows = {};
  }

  onRowExpand(event: TableRowExpandEvent) {
    let truckDetails = event.data.truckDetails;
    // if (!truckDetails) {
    //   this.truckService.getTruckByTransportId(event.data.id).subscribe({
    //     next: (res: any) => {
    //       event.data.truckDetails = res;
    //     },
    //     error: (err: any) => {

    //     },
    //   })
    // }
  }

  onRowCollapse(event: TableRowCollapseEvent) {
    // this.messageService.add({ severity: 'success', summary: 'Product Collapsed', detail: event.data.name, life: 3000 });
  }

}
