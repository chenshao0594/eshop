import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , DataUtils, AlertService } from 'ng-jhipster';

import { EmailTemplate } from './email-template.model';
import { EmailTemplateService } from './email-template.service';

@Component({
    selector: 'jhi-email-template-detail',
    templateUrl: './email-template-detail.component.html'
})
export class EmailTemplateDetailComponent implements OnInit, OnDestroy {

    emailTemplate: EmailTemplate;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private emailTemplateService: EmailTemplateService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['emailTemplate']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmailTemplates();
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
    previousState() {
        window.history.back();
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
        this.eventManager.broadcast({ name: 'emailTemplateModification', content: 'OK'});
        this.isSaving = false;
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmailTemplates() {
        this.eventSubscriber = this.eventManager.subscribe('emailTemplateListModification', (response) => this.load(this.emailTemplate.id));
    }
}
