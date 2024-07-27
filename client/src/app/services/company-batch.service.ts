import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CompanyBatchService {
  constructor(private http: HttpClient) { }
  getAll() {
    return this.http.get('/api/v1/company/batch');
  }

  getTrack(id: any) {
    return this.http.get(`/api/v1/company/batch/${id}/track`);
  }

  produceBatch(data: any) {
    return this.http.post('/api/v1/company/batch', data);
  }
}
