import { Injectable } from '@angular/core';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class SharedDataServiceService {

  constructor() { }

  setUserData(user:User){
    sessionStorage.setItem('user',JSON.stringify(user))
  }

  getUserData(){
    return JSON.parse(sessionStorage.getItem('user'))
  }
}
