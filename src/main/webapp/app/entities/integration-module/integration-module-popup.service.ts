import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IntegrationModule } from './integration-module.model';
import { IntegrationModuleService } from './integration-module.service';
@Injectable()
export class IntegrationModulePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private integrationModuleService: IntegrationModuleService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.integrationModuleService.find(id).subscribe((integrationModule) => {
                if (integrationModule.createdDate) {
                    integrationModule.createdDate = {
                        year: integrationModule.createdDate.getFullYear(),
                        month: integrationModule.createdDate.getMonth() + 1,
                        day: integrationModule.createdDate.getDate()
                    };
                }
                if (integrationModule.lastModifiedDate) {
                    integrationModule.lastModifiedDate = {
                        year: integrationModule.lastModifiedDate.getFullYear(),
                        month: integrationModule.lastModifiedDate.getMonth() + 1,
                        day: integrationModule.lastModifiedDate.getDate()
                    };
                }
                this.integrationModuleModalRef(component, integrationModule);
            });
        } else {
            return this.integrationModuleModalRef(component, new IntegrationModule());
        }
    }

    integrationModuleModalRef(component: Component, integrationModule: IntegrationModule): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.integrationModule = integrationModule;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
