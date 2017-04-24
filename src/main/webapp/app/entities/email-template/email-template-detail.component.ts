import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , DataUtils } from 'ng-jhipster';

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

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private emailTemplateService: EmailTemplateService,
        private route: ActivatedRoute
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmailTemplates() {
        this.eventSubscriber = this.eventManager.subscribe('emailTemplateListModification', (response) => this.load(this.emailTemplate.id));
    }
}
