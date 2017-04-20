import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { VERSION, DEBUG_INFO_ENABLED } from '../../app.constants';

@Component({
    selector: 'jhi-menu',
    templateUrl: './menu.component.html',
    styleUrls: [
        'menu.scss'
    ]
})
export class MenuComponent implements OnInit {

    inProduction: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    isCollapseMerchant: boolean;
    isCollapseCategory: boolean;
    isCollapseCustomer: boolean;
    isCollapsedOrder: boolean;
    isCollapsedSetting: boolean;
    constructor(
        private languageService: JhiLanguageService,
        private router: Router
    ) {
        this.languageService.addLocation('home');
        this.isCollapseMerchant = true;
        this.isCollapseCategory = true;
        this.isCollapseCustomer = true;
        this.isCollapsedOrder = true;
        this.isCollapsedSetting = true;
    }

    ngOnInit() {
    }

    collapseNavbar() {
//        this.isNavbarCollapsed = true;
    }
    toggleNavbar() {
    }

}
