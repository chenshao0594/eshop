import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { EmailTemplate } from './email-template.model';
import { EmailTemplatePopupService } from './email-template-popup.service';
import { EmailTemplateService } from './email-template.service';

@Component({
    selector: 'jhi-email-template-dialog',
    templateUrl: './email-template-dialog.component.html'
})
export class EmailTemplateDialogComponent implements OnInit {

    emailTemplate: EmailTemplate;
    authorities: any[];
    isSaving: boolean;
            constructor(
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private emailTemplateService: EmailTemplateService,
        private eventManager: EventManager,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['emailTemplate']);
    }

    ngOnInit() {
        this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.load(params['id']);
            } else {
                this.emailTemplate = new EmailTemplate();
            }
        });
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    load(id) {
         this.emailTemplateService.find(id).subscribe((emailTemplate) => {
                this.emailTemplate = emailTemplate;
                
         });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, emailTemplate, field, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                emailTemplate[field] = base64Data;
                emailTemplate[`${field}ContentType`] = file.type;
            });
        }
    }
    clear() {
       // this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.emailTemplate.id !== undefined) {
            this.emailTemplateService.update(this.emailTemplate)
                .subscribe((res: EmailTemplate) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.emailTemplateService.create(this.emailTemplate)
                .subscribe((res: EmailTemplate) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: EmailTemplate) {
        this.eventManager.broadcast({ name: 'emailTemplateListModification', content: 'OK'});
        this.isSaving = false;
      //  this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-email-template-popup',
    template: ''
})
export class EmailTemplatePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emailTemplatePopupService: EmailTemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.emailTemplatePopupService
                    .open(EmailTemplateDialogComponent, params['id']);
            } else {
                this.modalRef = this.emailTemplatePopupService
                    .open(EmailTemplateDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
