import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class PublicService {
  constructor(private http: HttpClient) { }

  getOneTrackInfo(companyName: any, id: any) {
    return this.http.get(`/api/v1/public/${companyName}/${id}`);
  }

}
