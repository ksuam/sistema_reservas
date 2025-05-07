import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { MenubarModule } from 'primeng/menubar';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [MenubarModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {
  items: MenuItem[] | undefined;
  constructor(private route:Router){

  }
  ngOnInit() {
      this.items = [
          {
              label: 'Home',
              icon: 'pi pi-home',
              routerLink: '/home'
          },
          {
              label: 'Cerrar Sesion',
              icon: 'pi pi-logout',
              command: () => this.logout(),
          }
        ]
}

logout(){
  //("cerrar")
  sessionStorage.removeItem('user')
  this.route.navigate(['/'])

}
}
