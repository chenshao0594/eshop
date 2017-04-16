import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { EmailTemplate } from './email-template.model';
import { EmailTemplatePopupService } from './email-template-popup.service';
import { EmailTemplateService } from './email-template.service';

@Component({
    selector: 'jhi-email-template-delete-dialog',
    templateUrl: './email-template-delete-dialog.component.html'
})
export class EmailTemplateDeleteDialogComponent {

    emailTemplate: EmailTemplate;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private emailTemplateService: EmailTemplateService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['emailTemplate']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emailTemplateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'emailTemplateListModification',
                content: 'Deleted an emailTemplate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-email-template-delete-popup',
    template: ''
})
export class EmailTemplateDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emailTemplatePopupService: EmailTemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.emailTemplatePopupService
                .open(EmailTemplateDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
