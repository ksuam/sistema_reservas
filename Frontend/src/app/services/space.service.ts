import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Space } from '../models/Space';

@Injectable({
  providedIn: 'root'
})
export class SpaceService {
  appUrl:string = environment.appUrl
  api:string = environment.spacesApi
  constructor(private http: HttpClient) { }

  getSpaces():Observable<Space[]>{
    return this.http.get<Space[]>(this.appUrl+this.api)
  }
}
