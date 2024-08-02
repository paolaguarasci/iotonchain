import { Component, OnInit } from '@angular/core';
import { TransportService } from '../../services/transport.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transport',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transport.component.html',
  styleUrl: './transport.component.scss'
})
export class TransportComponent implements OnInit {

  res !: any;

  constructor(private transportService: TransportService) {

  }

  ngOnInit() {
    this,this.transportService.getAll().subscribe((res: any) => {
      this.res = res;
    })
  }

}
