import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EmailTemplateDetailComponent } from '../../../../../../main/webapp/app/entities/email-template/email-template-detail.component';
import { EmailTemplateService } from '../../../../../../main/webapp/app/entities/email-template/email-template.service';
import { EmailTemplate } from '../../../../../../main/webapp/app/entities/email-template/email-template.model';

describe('Component Tests', () => {

    describe('EmailTemplate Management Detail Component', () => {
        let comp: EmailTemplateDetailComponent;
        let fixture: ComponentFixture<EmailTemplateDetailComponent>;
        let service: EmailTemplateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [EmailTemplateDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EmailTemplateService,
                    EventManager
                ]
            }).overrideComponent(EmailTemplateDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmailTemplateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailTemplateService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EmailTemplate(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.emailTemplate).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
