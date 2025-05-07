import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { MenuComponent } from './components/menu/menu.component';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,ButtonModule,MenuComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'reservas';
  constructor(private primengConfig: PrimeNGConfig, private router: Router) {}
  isNotRootRoute(): boolean {
    return this.router.url !== '/';
  }
ngOnInit(){
  this.primengConfig.ripple = true
}
}
