import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TruckService {
  constructor(private http: HttpClient) {}

  getTruckByTransportId(id: any) {
    return this.http.get(`/api/v1/company/transport/${id}/truck`);
  }

  createOne(data: any) {
    return this.http.post('/api/v1/company/truck', data);
  }

  getAll() {
    return this.http.get(`/api/v1/company/truck`);
  }
}
