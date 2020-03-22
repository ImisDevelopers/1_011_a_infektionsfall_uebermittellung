from random import randint, randrange, choice, sample

last_names = ['Peters', 'Müller', 'Schulz', 'Schulze', 'Weber', 'Wagner', 'Richter', 'Klein', 'Bauer', 'Schröder',
              'Lange', 'Winkler', 'Winter']

male_first_names = ['Peter', 'Daniel', 'Hans', 'Franz', 'Karl', 'Tim', 'Jan', 'Jens', 'Kai']
female_fist_names = ['Jana', 'Lisa', 'Anna', 'Annika', 'Petra', 'Marie', 'Susanne', 'Daniela', 'Petra']

genders = ['männlich', 'weiblich']

email_providers = ['sender', 'postmailer', ]

insurance_companies = ['AOK', 'Barmer', 'Techniker Krankenkasse', 'IKK Nord', 'KNAPPSCHAFT', 'DAK Gesundheit']

symptoms = ['Husten', 'Fieber', 'Schnupfen', 'Erkältung', 'Atemschwierigkeiten', 'Kopfschmerzen', 'Halschmerzen', 'Gelenkschmerzen'] 

def gen_date_of_birth():
    year = 2020 - randint(15, 95)
    month = randrange(12) + 1
    day = randrange(28) + 1 if month == 2 else randrange(31) + 1
    return f'{year}-{month}-{day}'

def insurance_number():
    upper_alphabet = string.ascii_uppercase
    random_letter = random.choice(upper_alphabet)
    number = randint(100000000,999999999)
    return f'{random_letter}{number}'

def main():
    gender = choice(genders)
    firstName = choice(male_first_names) if gender == 'männlich' else choice(female_fist_names)

    person = {
        'lastName': choice(last_names),
        'firstName': firstName,
        'gender': gender,
        'dateOfBirth': gen_date_of_birth(),
        'email': f'{firstName[0]}@{choice(email_providers)}.de',
        'phoneNumber': 'telefonNr',
        'street': 'Street',
        'houseNumber': 'HouseNr',
        'zip': '654321',
        'city': 'City',
        'insuranceCompany': choice(insurance_companies),
        'insuranceMembershipNumber': insurance_number(),
        'fluImmunization': choice(['Ja', 'Nein']),
        'speedOfSymptomsOutbreak': choice(['Langsam', 'Mittel', 'Schnell']),
        'symptoms': sample(symptoms, random.randint(0, len(symptoms))),
        'coronaContacts': choice(['Ja', 'Nein']),
        'riskAreas': [
            'Southcorea',
            'China',
            'France'
        ],
        'weakenedImmuneSystem': 'false',
        'preIllnesses': [
            'cancer',
            'circulatory disorder'
        ]
    }


if __name__ == '__main__':
    main()
