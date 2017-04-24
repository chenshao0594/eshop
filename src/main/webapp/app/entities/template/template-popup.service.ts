import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Template } from './template.model';
import { TemplateService } from './template.service';
@Injectable()
export class TemplatePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private templateService: TemplateService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.templateService.find(id).subscribe((template) => {
                if (template.created_date) {
                    template.created_date = {
                        year: template.created_date.getFullYear(),
                        month: template.created_date.getMonth() + 1,
                        day: template.created_date.getDate()
                    };
                }
                if (template.last_modified_date) {
                    template.last_modified_date = {
                        year: template.last_modified_date.getFullYear(),
                        month: template.last_modified_date.getMonth() + 1,
                        day: template.last_modified_date.getDate()
                    };
                }
                this.templateModalRef(component, template);
            });
        } else {
            return this.templateModalRef(component, new Template());
        }
    }

    templateModalRef(component: Component, template: Template): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.template = template;
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
