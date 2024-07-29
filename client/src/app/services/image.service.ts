import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ImageService {
  api_key = environment.pyxabaiKey
  constructor(private http: HttpClient) { }

  getOne(key: any) {
    return this.http.get(`https://pixabay.com/api/?key=${this.api_key}&q=${key}&lang=it&editors_choice=true&image_type=photo&safesearch=true&orientation=horizontal`);
  }
}
