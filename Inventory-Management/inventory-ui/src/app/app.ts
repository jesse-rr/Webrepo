import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AsideNavComponent } from "./shared/aside-nav-component/aside-nav-component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AsideNavComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected title = 'inventory-ui';
}
