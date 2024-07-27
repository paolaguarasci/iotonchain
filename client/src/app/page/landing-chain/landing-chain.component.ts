import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentService } from '../../services/document.service';
import { saveAs } from 'file-saver';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { TagModule } from 'primeng/tag';

import { ethers } from 'ethers';
import { sha3_256 } from 'js-sha3';

@Component({
  selector: 'app-landing-chain',
  standalone: true,
  imports: [CommonModule, PdfViewerModule, TagModule],
  templateUrl: './landing-chain.component.html',
  styleUrl: './landing-chain.component.scss',
})
export class LandingChainComponent implements OnInit {
  id!: any;
  doc!: any;
  filename!: any;
  isPdf = false;
  preview!: any;
  fileHash!: any;
  scannerLink = 'https://amoy.polygonscan.com/search?f=0&q=';
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private documentService: DocumentService
  ) {}
  ngOnInit() {
    this.route.paramMap.subscribe((res: any) => {
      this.id = res.get('id');

      this.documentService.getOneDoc(this.id).subscribe({
        next: (res: any) => {
          this.doc = res;
        },
        error: (err: any) => {},
      });
      this.documentService.dowloadOne(this.id).subscribe({
        next: (res: any) => {
          this.filename = res.headers
            .get('content-disposition')
            .replace('attachment; filename="', '')
            .replace('"', '');

          if (res.body) {
            const reader = new FileReader();
            reader.onload = () => {
              this.isPdf = this.filename.includes('.pdf');
              this.preview = reader.result as string;

            };
            reader.readAsDataURL(res.body);


            this.calculateHash(res.body);
          } else {
            this.preview = '';
          }
        },
        error: (err: any) => {},
      });
    });
  }

  d() {
    saveAs(this.preview, this.filename);
  }

  formatDataString(data: any) {
    return new Date(data).toLocaleDateString();
  }

  calculateHash(data: any) {
    const file = data;

    if (!file) {
      alert('Nessun file caricato!');
      return;
    }

    const reader = new FileReader();
    let that = this;
    reader.onload = (e: any) => {
      const buffer = e.target.result;
      that.fileHash = sha3_256(buffer);
      console.log(`${buffer} - ${that.fileHash}`);
    };
    reader.readAsArrayBuffer(file);
  }
}
